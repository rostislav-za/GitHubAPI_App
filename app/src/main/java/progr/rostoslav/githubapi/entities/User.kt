package progr.rostoslav.githubapi.entities

class User (
    val email: String,
    val password: String){
   var key=email+password
}