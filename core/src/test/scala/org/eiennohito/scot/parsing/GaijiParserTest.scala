package org.eiennohito.scot.parsing

import org.scalatest.matchers.ShouldMatchers
import org.scalatest.FunSuite
import org.eiennohito.scot.epwing.simple.gaiji.{GaijiLiteral, GaijiParser}

/**
 * @author eiennohito
 * @since 09.12.11 
 */

class GaijiParserTest extends FunSuite with ShouldMatchers {
  
  test("should parse gaiji string") {
    val str = "[GAIJI=w0123]"
    val res = GaijiParser.parseAll(GaijiParser.gaiji, str)
    res.successful should be (true)
    res.get should be (GaijiLiteral("w0123"))
  }

  test("should parse simple article") {
    
    val article = "ばか‐しょうじき【馬▼鹿正直】─シャウヂキ[GAIJI=wB03E]名・形動[GAIJI=wB03F]\nただ正直なばかりで融通がきかないこと。また、そのような人。  ‐さ"
    val res = GaijiParser.parse(article)
    
    res.isEmpty should be (false)
    val list = res.get
    list.size should equal (5)
  }

}