package mh

import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import groovy.util.logging.Log4j
import groovyx.net.http.HTTPBuilder
import java.util.logging.Level

/**
 * Created by IntelliJ IDEA.
 * User: mikher
 * Date: 2011-11-23
 * Time: 20.51
 * To change this template use File | Settings | File Templates.
 */
@Log4j
class TwitterAPIClient {


    public Object getHashTagFromTwitter() {
        // GET http://search.twitter.com/search.json?q=blue%20angels&rpp=5&include_entities=true&with_twitter_user_id=true&result_type=mixed
        def http = new HTTPBuilder('http://search.twitter.com')
        def jsonResp = http.get(path: '/search.json', query: [q: 'Groovy&rpp=5&include_entities=true&with_twitter_user_id=true&result_type=mixed'])
        def slurper = new JsonSlurper()
        // def json = slurper.parseText(jsonResponse)
        // println JsonOutput.prettyPrint(jsonResp​​​​​​)
        // log.log(Level.INFO, jsonResponse)
        return jsonResp
    }
}
