import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property

class MicroStrategyWarExtension {
    final Property<String> outputFileName

    @javax.inject.Inject
    MicroStrategyWarExtension(ObjectFactory objects) {
        outputFileName = objects.property(String)
    }
}
