package com.application.assignmentkotlin.db


data class Owners (  var id :Int,
                  var login :String?,
                   var node_id:String?,
                  var avatar_url:String?,
                  var gravatar_id :String?,
                  var url :String?,
                  var html_url :String?,
                  var followers_url :String?,
                  var following_url :String?,
                  var gists_url :String?,
                  var starred_url :String?,
                  var subscriptions_url :String?,
                  var organizations_url :String?,
                  var repos_url :String?,
                  var events_url :String?,
                  var received_events_url :String?,
                  var type :String?,
                  var site_admin :String? )
