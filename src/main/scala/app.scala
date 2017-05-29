import java.io.{File, StringReader, StringWriter}
import javax.xml.transform.TransformerFactory
import javax.xml.transform.stream.{StreamResult, StreamSource}

import scala.io.Source

/**
  * Created by axt on 2017/05/29.
  */
object app extends App {

  override def main(args:Array[String])= {
    val target = Convert(new File(args.head), Source.fromFile(args.tail.head).getLines().mkString(""))
    Console.println(target)
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
}
