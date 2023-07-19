package com.puzzling.puzzlingaos.presentation.home.personal

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.puzzling.puzzlingaos.domain.entity.ActionPlan
import com.puzzling.puzzlingaos.domain.entity.MyPuzzleBoard
import com.puzzling.puzzlingaos.domain.repository.MyBoardRepository
import com.puzzling.puzzlingaos.util.UserInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PersonalDashboardViewModel @Inject constructor(
    private val repository: MyBoardRepository,
) : ViewModel() {
    private val _myNickname = MutableLiveData<String>()
    val myNickname: LiveData<String> get() = _myNickname
    private val _myPuzzleCount = MutableLiveData<Int>()
    val myPuzzleCount: LiveData<Int> get() = _myPuzzleCount
    private val _isReviewDay = MutableLiveData<Boolean>()
    val isReviewDay: LiveData<Boolean> get() = _isReviewDay
    private val _hasTodayReview = MutableLiveData<Boolean>()
    val hasTodayReview: LiveData<Boolean> get() = _hasTodayReview

    private var _myPuzzleBoardList: MutableLiveData<List<MyPuzzleBoard>> = MutableLiveData()
    val myPuzzleBoardList: LiveData<List<MyPuzzleBoard>>
        get() = _myPuzzleBoardList

    private val _myPuzzleImage = MutableLiveData<List<String>>()
    val myPuzzleImage: LiveData<List<String>> get() = _myPuzzleImage

    private val _myReviewDate = MutableLiveData<List<String>>()
    val myReviewDate: LiveData<List<String>> get() = _myReviewDate

    private val _myReviewId = MutableLiveData<List<Int>>()
    val myReviewId: LiveData<List<Int>> get() = _myReviewId

    private var _actionPlanList: MutableLiveData<List<ActionPlan>> = MutableLiveData(null)
    val actionPlanList: LiveData<List<ActionPlan>>
        get() = _actionPlanList

    private val _isSuccess = MutableLiveData(false)
    val isSuccess: LiveData<Boolean> get() = _isSuccess

    init {
        getMyPuzzleData()
        getMyPuzzleData()
        getActionPlan()
        getMyPuzzleBoard()
    }

    private fun getMyPuzzleData() = viewModelScope.launch {
        repository.getUserPuzzle(1, 3, "2023-07-05")
            .onSuccess { response ->
                _isSuccess.value = true
                Log.d("personal", "getMyPuzzleData() success:: $response")
                _myNickname.value = response.data.myPuzzle.nickname
                _myPuzzleCount.value = response.data.myPuzzle.puzzleCount
                _isReviewDay.value = response.data.isReviewDay
                _hasTodayReview.value = response.data.hasTodayReview
                Log.d("personal", "myNickname success:: ${_myNickname.value}")
                Log.d("personal", "puzzleCount success:: ${_myPuzzleCount.value}")
                Log.d("personal", "hasTodayReview success:: ${_hasTodayReview.value}")
            }
            .onFailure {
                Log.d("personal", "getMyPuzzleData() Fail:: $it")
            }
    }

    private fun getMyPuzzleBoard() = viewModelScope.launch {
        repository.getUserPuzzleBoard(UserInfo.MEMBER_ID, UserInfo.PROJECT_ID, "2023-07-05")
            .onSuccess { response ->
                _myPuzzleBoardList.value = response
                Log.d("personal", "getMyPuzzleBoard() success:: $response")

                val myPuzzles: List<MyPuzzleBoard> =
                    _myPuzzleBoardList.value!!
                _myReviewDate.value = myPuzzles.map { it.reviewDate ?: "" }
                _myReviewId.value = myPuzzles.map { it.reviewId ?: -1 }
                _myPuzzleImage.value = myPuzzles.map { it.puzzleAssetName }
                Log.d("personal", "_myReviewDate:: ${_myReviewDate.value}")
                Log.d("personal", "_myReviewId:: ${_myReviewId.value}")
                Log.d("personal", "_myPuzzleImage:: ${_myPuzzleImage.value}")
            }
            .onFailure {
                Log.d("personal", "getMyPuzzleBoard() Fail:: $it")
            }
    }

    private fun getActionPlan() = viewModelScope.launch {
        repository.getActionPlan(UserInfo.MEMBER_ID, UserInfo.PROJECT_ID).onSuccess { response ->
            Log.d("personal", "getActionPlan() success:: $response")
            _actionPlanList.value = response
        }.onFailure {
            Log.d("personal", "getActionPlan() Fail:: $it")
        }
    }

//    val actionPlanList: List<ActionPlan> =
//        listOf(
//            ActionPlan("여기에는 글이 계속 작성되다가 작성되다가 작성되다가 작성되다가 이쯤 되면 끊기게 돼...", "7월 3일"),
//            ActionPlan("여기에는 글이 계속 작성되다가 작성되다가 작성되다가 작성되다가 이쯤 되면 끊기게 돼...", "7월 4일"),
//            ActionPlan("여기에는 글이 계속 작성되다가 작성되다가 작성되다가 작성되다가 이쯤 되면 끊기게 돼...", "7월 5일"),
//            ActionPlan("여기에는 글이 계속 작성되다가 작성되다가 작성되다가 작성되다가 이쯤 되면 끊기게 돼...", "7월 6일"),
//            ActionPlan("여기에는 글이 계속 작성되다가 작성되다가 작성되다가 작성되다가 이쯤 되면 끊기게 돼...", "7월 7일"),
//        )

    val _bottomButtonText = MutableLiveData<String>()
    val bottomButtonText: String
        get() = _bottomButtonText.value ?: "회고 작성일이 아니에요"
}
