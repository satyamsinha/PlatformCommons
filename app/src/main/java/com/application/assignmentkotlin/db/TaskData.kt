package com.application.assignmentkotlin.db

data class TaskData(
    var id :Int,
    var node_id :String?,
    var name :String?,
    var full_name :String?,
    var private :String?,
    var owner:Owners,
    var html_url: String?,
    var description : String?,
    var fork : String?,
    var url : String?,
    var forks_url : String?,
    var keys_url : String?,
    var collaborators_url  : String?,
    var teams_url : String?,
    var hooks_url  : String?,
    var issue_events_url : String?,
    var events_url : String?,
    var assignees_url:String?,
    var branches_url : String?,
    var tags_url : String?,
    var blobs_url : String?,
    var git_tags_url : String?,
    var git_refs_url : String?,
    var trees_url : String?,
    var statuses_url  : String?,
    var languages_url: String?,
    var stargazers_url : String?,
    var contributors_url : String?,
    var subscribers_url : String?,
    var subscription_url: String?,
    var commits_url  : String?,
    var git_commits_url: String?,
    var comments_url: String?,
    var issue_comment_url: String?,
    var contents_url: String?,
    var compare_url : String?,
    var merges_url: String?,
    var archive_url: String?,
    var downloads_url: String?,
    var issues_url: String?,
    var pulls_url : String?,
    var milestones_url   : String?,
    var notifications_url: String?,
    var labels_url : String?,
    var releases_url: String?,
    var deployments_url :String )