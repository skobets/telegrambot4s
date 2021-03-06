package info.mukel.telegrambot4s

import info.mukel.telegrambot4s.api.RequestHandler
import info.mukel.telegrambot4s.methods.ApiRequest

import scala.concurrent.Future
import scala.language.implicitConversions

/**
  * Useful implicits to reduce boilerplate.
  */
object Implicits {
  implicit def toEitherLeft [L, R](l: L) : Either[L, R] = Left(l)
  implicit def toEitherRight[L, R](r: R) : Either[L, R] = Right(r)

  implicit def toOptionEitherLeft [L, R](l: L) : Option[Either[L, R]] = Option(Left(l))
  implicit def toOptionEitherRight[L, R](r: R) : Option[Either[L, R]] = Option(Right(r))

  implicit def toOption[T](v: T) : Option[T] = Option(v)

  implicit class MarkdownString(s: String) {
    def bold = s"*$s*"
    def italic = s"_${s}_"
    def urlWithAlt(alt: String) = s"[$alt]($s)"
    def altWithUrl(url: String) = s"[$s]($url)"
    def inlineCode = s"`$s`"
    def blockCode(language: String = "text") = s"```$language\n$s\n```"
  }

  implicit class TaggedString(s: String) {
    def prefixTag(tag: String) = tag + s
    def untag(tag: String) = s.stripPrefix(tag)
  }

  implicit class SuffixRequests[R: Manifest](r: ApiRequest[R]) {
    def request(implicit client: RequestHandler): Future[R] = client(r)
  }
}
