package com.hendisantika.comment.repository

import scala.reflect.runtime.{ universe => ru }
import com.hendisantika.comment.util.ReflectionSugars._
import io.getquill.ast._
import io.getquill.{FinagleMysqlContext, SnakeCase}

/**
  * Created by hendisantika on 10/16/16.
  */
trait BaseRepository{
  val ctx: FinagleMysqlContext[SnakeCase]

  import ctx._

  def makeSelectiveUpdate[T, UT <: Product](q: Quoted[T], updateEntity: UT)(implicit tt: ru.TypeTag[UT]): Quoted[Update[T]] =
    new Quoted[Update[T]] {
      override def ast: Ast = {
        var i = 0
        val updates = getCaseClassMethodNames(ru.typeOf(tt)) flatMap { name =>
          val value = updateEntity.productElement(i)
          val pair = value match {
            // TODO there are weaknesses that the pair should only be primitive
            case Some(e) => Some(name -> e)
            case _ => None
          }
          i += 1
          pair
        }
        if(updates.isEmpty)
          throw new IllegalArgumentException("Selective Update failed, No field is set !")

        Update(q.ast, updates.map(entry =>
          Assignment(Ident("v"), Property(Ident("v"), entry._1), Constant(entry._2))
        ))
      }
    }
}
