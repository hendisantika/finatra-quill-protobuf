package com.hendisantika.comment.util

import scala.reflect.runtime.universe._

/**
  * Created by hendisantika on 10/3/16.
  */
object ReflectionSugars {

  private lazy val universeMirror = runtimeMirror(getClass.getClassLoader)

  def companionOf[RT](t: Type) = {
    val companionMirror = universeMirror.reflectModule(t.typeSymbol.companion.asModule)
    companionMirror.instance.asInstanceOf[RT]
  }

  def companionOf[T : TypeTag, RT] : RT =
    companionOf(typeOf(implicitly[TypeTag[T]]))

  def innerTypeCompanionOf[T : TypeTag, RT] : RT = {
    val t = implicitly[TypeTag[T]].tpe.typeArgs.head
    companionOf(t)
  }

  def getCaseClassMethods(t: Type) = t.members.sorted.collect {
    case m: MethodSymbol if m.isCaseAccessor => m
  }

  def getCaseClassMethods[T: TypeTag]: List[MethodSymbol] = {
    val tt = implicitly[TypeTag[T]]
    getCaseClassMethods(typeOf(tt))
  }

  def getCaseClassMethodNames(t: Type) = t.members.sorted.collect {
    case m: MethodSymbol if m.isCaseAccessor => m.name.toString
  }

  def getCaseClassMethodNames[T: TypeTag]: List[String] = {
    val tt = implicitly[TypeTag[T]]
    getCaseClassMethodNames(typeOf(tt))
  }
}
