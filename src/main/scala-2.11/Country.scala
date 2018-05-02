
class Country(val name: String) {

  private val _name: String = name

  def getName:String = {
    _name
  }
}
