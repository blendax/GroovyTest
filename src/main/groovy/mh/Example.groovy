//
// Generated from archetype; please customize.
//

package mh

import groovy.util.logging.Log4j
import org.apache.log4j.Level

/**
  * Example Groovy class.
 */
@Log4j
class Example {
    def show() {
        log.setLevel(Level.INFO)
        println 'Hello World'
        log.info("Log from Groovy Log annotation")
    }

    def useAMavenDependency() {

    }
}
