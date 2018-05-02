
class Parser (val v: String) {
  private var vCountry = v.split("]")(0)
  private var vPrince = v.split("]")(1)
  vCountry = vCountry.replace("[", "").replace("]", "")
  vPrince = vPrince.replace("[", "").replace("]", "")

  private val c1 = vCountry.split(",")(0)
  private val c2 = vCountry.split(",")(1)
  private val country = new Country(if(c1 != "null") c1 else c2)

  private val pC = vPrince.split(",")(0)
  private val pT = vPrince.split(",")(1)
  private val pV = vPrince.split(",")(2).toDouble

  private val price = new Price(pC, pT, pV)

  def getPrice:Price = {
    price
  }

  def getCountry:Country = {
    country
  }
}
