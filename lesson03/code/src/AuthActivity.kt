import java.util.*

class AuthActivity {
    fun onCreate() {
        while (!validateEmail(askEmail())) {
            showWrongEmail()
        }

        while (!validatePassword(askPassword())) {
            showWrongPassword()
        }

        showProgress()

        

    }

    private fun showProgress() {
        println("Authorizing...")
    }

    fun showWrongPassword() {
        println("Wrong Password")
    }

    fun showWrongEmail() {
        println("Wrong Email")
    }

    fun askEmail(): String = Scanner(System.`in`).nextLine() // emailEditText.text

    fun askPassword(): String = Scanner(System.`in`).nextLine() // passwordEditText.text

    fun validateEmail(email: String): Boolean = email.length in 5..19 && email.endsWith("@mail.ru")

    fun validatePassword(password: String): Boolean = password.length in 5..19
}