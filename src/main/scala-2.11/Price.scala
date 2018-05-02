
class Price(val i_currency: String, val i_type: String, val i_value: Double) {

  private val _i_currency: String = i_currency
  private val _i_type: String = i_type
  private val _i_value: Double = i_value

  def getCurrency:String = {
    _i_currency
  }
  def getValue:Double = {
    _i_value
  }

  def getType:String = {
    _i_type
  }
}