import org.apache.spark.sql.SQLContext
import org.apache.spark.{SparkConf, SparkContext}
import collection.mutable
import java.io._

object main {

  def main(args: Array[String]): Unit = {

    val resourcesPath = getClass.getResource("/")

    println(resourcesPath)

    val conf = new SparkConf().setAppName("Simple Application").setMaster("local[*]")
    val sc = new SparkContext(conf)

    val sqlContext = new SQLContext(sc)
    val df = sqlContext.read
      .format("com.databricks.spark.xml")
      .option("rowTag", "NOTICE_DATA")
      .load(resourcesPath + "/*")

    val selectedData = df.select("ISO_COUNTRY", "VALUES")

    val data = selectedData.rdd.map(r => r(0) + (if(r(1)!=null)r(1).toString else "[-,-,-1]")).collect()
    sc.stop()

    val count = new mutable.HashMap[String, Int]
    val amount = new mutable.HashMap[String, PriceHelper]

    data.foreach{
        country =>
          val data = new Parser(country)
          val c = data.getCountry
          val p = data.getPrice

          //update countries counter
          if(!count.isDefinedAt(c.getName))
            count.put(c.getName, 1)
          else
            count.update(c.getName, count(c.getName) + 1)

          //update cash values
          if(p.getValue > 0) {
            val key = c.getName + "|" + p.getCurrency
            if (!amount.isDefinedAt(key))
              amount.put(key, new PriceHelper(p.getValue,1))
            else
              amount.update(key, amount(key).incr(p.getValue))
          }
    }

    var result:String="ZAMÓWIENIA (KRAJ|LICZBA): \n"

    for ((k,v) <- count) result += k + "|" + v + "\n"

    result+="\nŚREDNIA KWOTA (KRAJ|WALUTA|KWOTA) : \n"
    for ((k,v) <- amount) result+=k + "|" + v.getResult + "\n"

    // FileWriter
    val file = new File("result.txt")
    val bw = new BufferedWriter(new FileWriter(file))
    bw.write(result)
    bw.close()
  }

}
