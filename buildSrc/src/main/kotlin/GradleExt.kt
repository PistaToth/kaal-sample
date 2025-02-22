import org.gradle.api.Project
import org.gradle.api.publish.maven.MavenPom
import java.io.ByteArrayOutputStream

fun Project.findPropertyOrNull(s: String) = findProperty(s) as String?

fun Project.getProperty(propertyName: String) = property(propertyName)

fun Project.getPropertyOrDefaultValue(propertyName: String, defaultValue: Any) = findProperty(propertyName) ?: defaultValue

/**
 * Gets number of commits in projects repository.
 * @return Number of commits in projects repository.
 */
fun Project.getGitCommits(): Int {
    val stdout = ByteArrayOutputStream()
    exec {
        commandLine = listOf("git", "rev-list", "HEAD", "--count")
        standardOutput = stdout
    }
    return Integer.parseInt(stdout.toString().trim())
}

/**
 * Find out if build is tagged as snapshot or not.
 * @return true if build is tagged as snapshot.
 */
fun Project.isSnapshot(): Boolean {
    return if (project.hasProperty("version")) {
        project.property("version").toString().endsWith("-SNAPSHOT")
    } else {
        false
    }
}