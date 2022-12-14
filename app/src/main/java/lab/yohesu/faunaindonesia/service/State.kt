package lab.yohesu.faunaindonesia.service

import lab.yohesu.faunaindonesia.model.LeaderboardModel
import lab.yohesu.faunaindonesia.model.LearningModel

data class State<out T>(
    val status: Status,
    val data: T?,
    val message: String?
) {
    companion object{
        fun<T> success(data: T?): State<T & Any> {
            return State(Status.SUCCESS, data, null)
        }

        fun<T> error(msg: String): State<T>{
            return State(Status.ERROR, null, msg)
        }

        fun<T> loading(): State<T>{
            return State(Status.LOADING, null, null)
        }

        fun<T> idle(): State<T>{
            return State(Status.IDLE, null, null)
        }

        fun <T> successInsertToRoom(data: T?): State<T>{
            return State(Status.SUCCESS_INSERT_TO_ROOM, data, null)
        }

        fun <T> successDeleteToRoom(data: T?): State<T>{
            return State(Status.SUCCESS_DELETED_TO_ROOM, data, null)
        }
    }
}
