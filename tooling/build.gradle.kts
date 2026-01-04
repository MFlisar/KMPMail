/**
 * KMP Dev Tools - Tooling Build Script
 *
 * VERSION: 6.0.1
 */

import com.michaelflisar.kmpdevtools.core.utils.ProjectData
import com.michaelflisar.kmpdevtools.readme.UpdateReadmeUtil
import com.michaelflisar.kmpdevtools.tooling.MacActions
import com.michaelflisar.kmpdevtools.tooling.MacDefaults
import com.michaelflisar.kmpdevtools.tooling.ProjectActions
import com.michaelflisar.kmpdevtools.tooling.ToolingSetup

/** 1 - updateMarkdownFiles
 *
 * Updates the markdown files (README.md, etc.) based on the current configuration.
 *
 * FUNCTION DOES NOT NEED ANY ADJUSTMENTS!
 *
 * RESERVED README FOLDERS:
 * - `documentation/screenshots`: screenshots placed here will be displayed in the screenshots section automatically
 * - `documentation/modules`: markdown files placed here will be listed in the modules section automatically
 * - all other files will be listed under the more section
 *
 * CODE SNIPPETS (no whitespaces in name allowed!):
 * Example code:
 *      // begin-snippet: NAME
 *      some code here
 *      // end-snippet
 * Usage:
 *      place "snippet: NAME" anywhere in a markdown file
 */
tasks.register("updateMarkdownFiles") {

    // no configuration cache
    notCompatibleWithConfigurationCache("${this.name} does not support configuration cache")

    doLast {
        // doku: https://github.com/MFlisar/kmp-dev-tools/blob/main/docs/UpdateReadmeUtil.md
        UpdateReadmeUtil.update(project = rootProject)
    }
}

/** 2 - macActions
 *
 * Actions tasks - run it manually when needed
 *
 * - can copy the project to the mac via `delete` + `copy`
 * - can build the xcframework on the mac via ssh command and copy it back to windows
 * - can open the app/xcframework project on the mac inside XCode
 *
 * DOKU: https://github.com/MFlisar/kmp-dev-tools/blob/main/docs/MacActions.md
 */
tasks.register("macActions") {

    // no configuration cache
    notCompatibleWithConfigurationCache("${this.name} does not support configuration cache")

    doLast {
        val sshSetup = MacDefaults.getMacSSHSetup()
        val relativePathRoot = MacDefaults.getRelativePathRoot(rootProject)
        val toolingSetup = ToolingSetup(
            root = relativePathRoot
        )
        MacActions.run(
            project = rootProject,
            sshSetup = sshSetup,
            toolingSetup = toolingSetup
        )
    }
}

/** 3 - projectRenamer
 *
 * Projects renamer tasks - run it manually when needed
 *
 * Renames package names, folder names, project references, etc. based on the current configuration.
 *
 * DOKU: https://github.com/MFlisar/kmp-dev-tools/blob/main/docs/ProjectRenamer.md
 */
tasks.register("projectRenamer") {

    // no configuration cache
    notCompatibleWithConfigurationCache("${this.name} does not support configuration cache")

    doLast {
        val data = ProjectData(project = rootProject)
        ProjectActions.runProjectRenamer(data)
    }
}

/** 4 - projectActions
 *
 * Project actions tasks - run it manually when needed
 *
 * DOKU: https://github.com/MFlisar/kmp-dev-tools/blob/main/docs/ProjectActions.md
 * TODO: Implement actions as needed
 */
tasks.register("projectActions") {

    // no configuration cache
    notCompatibleWithConfigurationCache("${this.name} does not support configuration cache")

    doLast {

        // TODO
    }
}