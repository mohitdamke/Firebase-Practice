package com.example.firebasepractice.firReDb.repository

import com.example.firebasepractice.firReDb.RealtimeModelResponse
import com.example.firebasepractice.util.ResultState
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class RealtimeRepositoryImpl @Inject constructor(
    private val db: DatabaseReference
) : RealtimeRepository {
    override fun insert(items: RealtimeModelResponse.RealTimeItems): Flow<ResultState<String>> =
        callbackFlow {
            trySend(ResultState.Loading)
            db.push().setValue(
                items
            ).addOnCompleteListener {
                if (it.isSuccessful) {
                    trySend(ResultState.Success("Success data inserted"))
                }

            }.addOnFailureListener {
                trySend(ResultState.Failure(it))
            }
            awaitClose {
                close()
            }
        }

    override fun getItems(): Flow<ResultState<List<RealtimeModelResponse>>> = callbackFlow {

        val valueEvent = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val items = snapshot.children.map {
                    RealtimeModelResponse(
                        it.getValue(RealtimeModelResponse.RealTimeItems::class.java), key = it.key
                    )
                }
                trySend(ResultState.Success(items))
            }

            override fun onCancelled(error: DatabaseError) {
                trySend(ResultState.Failure(error.toException()))
            }
        }
        db.addValueEventListener(valueEvent)
        awaitClose {
            db.removeEventListener(valueEvent)
        }
    }

    override fun delete(key: String): Flow<ResultState<String>> = callbackFlow {

        trySend(ResultState.Loading)
        db.child(key).removeValue().addOnCompleteListener {
            if (it.isSuccessful) {
                trySend(ResultState.Success("Success data deleted"))
            }
        }.addOnFailureListener {
            trySend(ResultState.Failure(it))
        }
        awaitClose {
            close()
        }
    }

    override fun update(req: RealtimeModelResponse): Flow<ResultState<String>> = callbackFlow {

        trySend(ResultState.Loading)
        val map = HashMap<String, Any>()
        map["title"] = req.item?.title!!
        map["description"] = req.item.description!!
        db.child(req.key!!).updateChildren(map).addOnCompleteListener {
                trySend(ResultState.Success("Success data updated"))
            }.addOnFailureListener {
                trySend(ResultState.Failure(it))
            }
        awaitClose {
            close()
        }
    }
}