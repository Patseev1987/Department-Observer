package detekt

import io.gitlab.arturbosch.detekt.api.*
import io.gitlab.arturbosch.detekt.rules.hasAnnotation
import org.jetbrains.kotlin.psi.KtCallExpression
import org.jetbrains.kotlin.psi.KtFile
import org.jetbrains.kotlin.psi.KtNamedFunction

class LogUsingRule(config: Config) : Rule(config) {
    override val issue: Issue = Issue(
        javaClass.simpleName,
        Severity.CodeSmell,
        "Log.i or Log.d detected. ",
        Debt.FIVE_MINS
    )

    override fun visitKtFile(file: KtFile) {
        super.visitKtFile(file)
        if (file.name.contains("Test") || file.name.contains("test")) {
            return
        }
    }

    override fun visitNamedFunction(function: KtNamedFunction) {
        super.visitNamedFunction(function)

        if (isTestFunction(function)) {
            return
        }

        function.accept(object : DetektVisitor() {
            override fun visitCallExpression(expression: KtCallExpression) {
                super.visitCallExpression(expression)

                val callText = expression.text
                when {
                    callText.contains("Log.i(") -> {
                        report(
                            CodeSmell(
                                issue = issue,
                                entity = Entity.from(expression),
                                message = "Log.i() found in function '${function.name}'. " +
                                        "Log.i detected! Don't forget to delete!"
                            )
                        )
                    }

                    callText.contains("Log.d(") -> {
                        report(
                            CodeSmell(
                                issue = issue,
                                entity = Entity.from(expression),
                                message = "Log.d() found in function '${function.name}'. " +
                                        "Log.d detected! Don't forget to delete!"
                            )
                        )
                    }
                }
            }
        })
    }

    private fun isTestFunction(function: KtNamedFunction): Boolean {
        val functionName = function.name ?: return false
        return functionName.startsWith("test") ||
                functionName.contains("Test") ||
                function.hasAnnotation("Test") ||
                function.hasAnnotation("org.junit.Test")
    }
}

