package detekt

import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.RuleSet
import io.gitlab.arturbosch.detekt.api.RuleSetProvider

class ObserverRuleSetProvider : RuleSetProvider {
    override val ruleSetId: String = "ObserverRuleSet"

    override fun instance(config: Config): RuleSet {
        return RuleSet(
            ruleSetId,
            listOf(
                LogUsingRule(config),
            ),
        )
    }
}
