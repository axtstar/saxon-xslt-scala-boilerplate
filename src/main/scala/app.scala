import java.io.{File, StringReader, StringWriter}
import javax.xml.transform.TransformerFactory
import javax.xml.transform.stream.{StreamResult, StreamSource}

import net.sf.saxon.s9api._

import scala.io.Source

/**
  * Created by axt on 2017/05/29.
  */
object app extends App {

  override def main(args:Array[String])= {

    args.head match {
      case "xslt" =>
        val target = Convert(new File(args.tail.head), Source.fromFile(args.tail.tail.head).getLines().mkString(""))
        Console.println(target)
      case "xquery" =>
        val target = getXQuery(args.tail.head, Source.fromFile(args.tail.tail.head).getLines().mkString(""))
        Console.println(target)
    }
  }

  def Convert(stylesheet:File, xml:String): String = {

    val transformerFactory:TransformerFactory =  new net.sf.saxon.TransformerFactoryImpl()

    val source = new StreamSource(new StringReader(xml))
    val writer = new StringWriter()
    val outputTarget:StreamResult = new StreamResult(writer)

    val transformer = transformerFactory.newTransformer(new StreamSource(stylesheet))

    transformer.transform(source, outputTarget)
    val result = writer.toString
    writer.close()

    result
  }

  def getXQuery(xquery:String,xml:String):String ={
    // the Saxon processor object
    val saxon = new Processor(false)

    // compile the query
    val compiler = saxon.newXQueryCompiler()
    val exec = compiler.compile(xquery)

    // parse the string as a document node
    val builder = saxon.newDocumentBuilder()
    val src = new StreamSource(new StringReader(xml))
    val doc = builder.build(src)

    // instantiate the query, bind the input and evaluate
    val query = exec.load()
    query.setContextItem(doc)
    val result = query.evaluate()
    result.asInstanceOf[XdmNode].getStringValue
  }
}
