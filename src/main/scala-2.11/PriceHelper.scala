
class PriceHelper(val value: Double, val counter: Int){
  private var _value = value
  private var _counter = counter


  def getValue:Double = {
    _value
  }
  def getCounter:Int = {
    _counter
  }
  def getResult:Double = {
    BigDecimal(_value/_counter).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble
  }

  def incr(v: Double):PriceHelper = {
    _value += v
    _counter+=1
    this
  }

}
