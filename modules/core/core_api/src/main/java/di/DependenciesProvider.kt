package di

interface DependenciesProvider {
    fun getCoreProvider(): CoreProvider
}