package org.fig
package model


case class Movie(name: String)

case class Review(review: String)

case class User(userId: String)


case class Watch(id: String, user: User, movie: Movie, preference: Int)


