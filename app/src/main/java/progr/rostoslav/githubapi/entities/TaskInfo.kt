package progr.rostoslav.githubapi.entities

import progr.rostoslav.githubapi.R

data class TaskInfo(
    val title:String ="Task",
    val text:String="Text",
    var isReady:Boolean=false
) {
    var isReadyImg=if(this.isReady) R.drawable.gt_checked else R.drawable.gt_not_checked
}