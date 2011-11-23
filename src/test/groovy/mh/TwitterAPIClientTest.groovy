package mh

import groovy.json.JsonSlurper
import groovy.json.JsonOutput
import groovyx.net.http.RESTClient
import groovyx.net.http.HTTPBuilder
import static groovyx.net.http.Method.GET
import static groovyx.net.http.ContentType.TEXT
import static groovyx.net.http.ContentType.JSON

/**
 * Created by IntelliJ IDEA.
 * User: mikher
 * Date: 2011-11-23
 * Time: 20.45
 * To change this template use File | Settings | File Templates.
 */
class TwitterAPIClientTest extends GroovyTestCase {

    def static twitterSearchUrl = 'http://search.twitter.com/search.json?q=%23vtd11'

    void testGetHashTagFromTwitter() {
        def twitterAPIClient = new TwitterAPIClient()
        def hashtagAPIRespons = twitterAPIClient.getHashTagFromTwitter()
        assert hashtagAPIRespons instanceof groovy.util.slurpersupport.GPathResult
        assert hashtagAPIRespons.HEAD.size() == 1
        assert hashtagAPIRespons.BODY.size() == 1
        assert hashtagAPIRespons != null
    }

    void testTwitterSearchResponse() {
        def twitterResp = '''{"completed_in":0.106,"max_id":138910573323755520,"max_id_str":"138910573323755520","page":1,"query":"%40Groovy","refresh_url":"?since_id=138910573323755520&q=%40Groovy","results":[{"created_at":"Tue, 22 Nov 2011 09:23:56 +0000","from_user":"rarasrarasraras","from_user_id":282975251,"from_user_id_str":"282975251","from_user_name":"raras annisa putri","geo":null,"id":138910573323755520,"id_str":"138910573323755520","iso_language_code":"nl","metadata":{"result_type":"recent"},"profile_image_url":"http://a2.twimg.com/profile_images/1642615713/329906200_normal.jpg","source":"&lt;a href=&quot;http://ubersocial.com&quot; rel=&quot;nofollow&quot;&gt;\u00DCberSocial&lt;/a&gt;","text":"@groovy bilyard \uE755\u02D8\u2022\u02D8\uE755 \u043D\u0251\u0251\u02D8\u00B0\u02D8\u043D\u0251\u0251\u02D8\u00B0\u02D8\u043D\u0251\u0251 \uE755\u02D8\u2022\u02D8\uE755","to_user":"groovy","to_user_id":10294942,"to_user_id_str":"10294942","to_user_name":"Altaf Kassam"},{"created_at":"Mon, 21 Nov 2011 06:27:35 +0000","from_user":"chibaf","from_user_id":26181376,"from_user_id_str":"26181376","from_user_name":"Fumihiro CHIBA","geo":null,"id":138503805036806144,"id_str":"138503805036806144","iso_language_code":"ja","metadata":{"result_type":"recent"},"profile_image_url":"http://a3.twimg.com/profile_images/1182192728/4548469_m_normal.jpg","source":"&lt;a href=&quot;http://book.akahoshitakuya.com/&quot; rel=&quot;nofollow&quot;&gt;\u8AAD\u66F8\u30E1\u30FC\u30BF\u30FC&lt;/a&gt;","text":"\u30DD\u30F3\u30B3\u30C4\u6226\u8266@Groovy(@ponkotuy)\u3092\u304A\u6C17\u306B\u5165\u308A\u306B\u8FFD\u52A0 \u2192http://t.co/cVDXDxZj #bookmeter","to_user":null,"to_user_id":null,"to_user_id_str":null,"to_user_name":null},{"created_at":"Fri, 18 Nov 2011 08:07:58 +0000","from_user":"Sportsguy1","from_user_id":43843223,"from_user_id_str":"43843223","from_user_name":"Gil Norse","geo":null,"id":137441904676708352,"id_str":"137441904676708352","iso_language_code":"en","metadata":{"result_type":"recent"},"profile_image_url":"http://a1.twimg.com/profile_images/310990307/Twitter_Picture_9_normal.jpg","source":"&lt;a href=&quot;http://twitter.com/&quot;&gt;web&lt;/a&gt;","text":"#FF@intanalwi @griffinGargoyle @JessicaNorthey @paul_steele @earthXplorer @pixel_jockey @Groovy","to_user":null,"to_user_id":null,"to_user_id_str":null,"to_user_name":null}],"results_per_page":15,"since_id":0,"since_id_str":"0"}'''
        def vtd11Resp = '{"completed_in":0.188,"max_id":136775691340488704,"max_id_str":"136775691340488704","page":1,"query":"%23vtd11","refresh_url":"?since_id=136775691340488704&q=%23vtd11","results":[{"created_at":"Wed, 16 Nov 2011 12:00:40 +0000","from_user":"tereseLindberg","from_user_id":215256647,"from_user_id_str":"215256647","from_user_name":"Terese Lindberg","geo":null,"id":136775691340488704,"id_str":"136775691340488704","iso_language_code":"es","metadata":{"result_type":"recent"},"profile_image_url":"http://a3.twimg.com/profile_images/1214811952/gravatar_normal.png","source":"&lt;a href=&quot;http://twitter.com/&quot;&gt;web&lt;/a&gt;","text":"RT @rodinanna: Tj\u00E4nstedesign fr\u00E5n Valtech experience days http://t.co/TooLucNK #servicedesign #vtd11","to_user":null,"to_user_id":null,"to_user_id_str":null,"to_user_name":null}],"results_per_page":15,"since_id":0,"since_id_str":"0"}'

        println JsonOutput.prettyPrint(vtd11Resp)
        def slurper = new JsonSlurper()
        def json = slurper.parseText(vtd11Resp)
        json.results.each {printMessage(it)}
    }

    void printMessage(message) {
        println "# encoded is = " + URLEncoder.encode("#")
        println "-------------- New Message ---------------"
        println "from_user: " + message.from_user
        println "created_at: " + message.created_at
        println "text: " + message.text
//        message.each {
        //            key, value ->
        //            println key + "=" + value
        //        }
    }

    void testRestGet() {
        // initialze a new builder and give a default URL
        // 'http://search.twitter.com/search.json?q=%23vtd11'
        def http = new HTTPBuilder('http://search.twitter.com/')

        http.request(GET, JSON) { req ->
            uri.path = 'search.json' // overrides any path in the default URL
            // headers.'User-Agent' = 'Mozilla/5.0'
            uri.query = [q: '#vtd11']

            response.success = { resp, reader ->
                assert resp.status == 200
                println "My response handler got response: ${resp.statusLine}"
                println "Response length: ${resp.headers.'Content-Length'}"
                System.out << reader // print response reader
            }

            // called only for a 404 (not found) status code:
            response.'404' = { resp ->
                println 'Not found'
            }
        }
    }


}
