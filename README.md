# 피드 화면
피드 화면은 사용자가 작성한 리뷰를 리스트로 보여주는 화면입니다.

이 모듈에서 구현하려는 기술:
* 멀티 모듈
* Jetpack Compose
* 최신 아키텍처 (UILayer, DataLayer)
* Unit test

## 스크린샷
<img src="screenshots/feed.png" width="800" height="400"/>

## 특징

### 멀티 모듈

Jitpack를 사용하여 이 모듈을 빌드 할 수 있게 적용하였습니다.

<img src="screenshots/jitpack.png" width="600" height="500"/>

아래 3가지 코드를 추가 하면 어떤 프로젝트든지 간단하게 화면을 적용 시킬 수 있습니다.

```
allprojects {
repositories {
...
maven { url 'https://jitpack.io' }
}
}
```

```
dependencies {
implementation 'com.github.sarang628:Feed:02a97b8010'
}
```

```
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TorangTheme {
                Column {
                    FeedScreen(
                        clickAddReview = {},
                        onRestaurant = {},
                        onName = {},
                        onImage = {},
                        onProfile = {},
                        ratingBar = { RatingBar(rating = it) }
                    )
                }
            }
        }

    }
}
```

### Jetpack Compose 사용

#### 최종 모듈 호출 함수
```
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TorangTheme {
                Column {
                    FeedScreen(
                        clickAddReview = {},
                        onRestaurant = {},
                        onName = {},
                        onImage = {},
                        onProfile = {},
                        ratingBar = { RatingBar(rating = it) }
                    )
                }
            }
        }

    }
}
```
```
/**
 * DI 모듈에서 제공하는 FeedScreen을 사용해주세요
 */
@Composable
fun _FeedsScreen(
    feedsViewModel: FeedsViewModel = hiltViewModel(),
    feeds: @Composable () -> Unit,
    torangToolbar: @Composable () -> Unit,
    errorComponent: @Composable () -> Unit,
    feedMenuBottomSheetDialog: @Composable (Boolean) -> Unit,
    commentBottomSheetDialog: @Composable (Boolean) -> Unit,
    shareBottomSheetDialog: @Composable (Boolean) -> Unit,
    emptyFeed: @Composable (Boolean) -> Unit,
    networkError: @Composable (Boolean) -> Unit,
    loading: @Composable (Boolean) -> Unit,
) {
    val uiState: FeedUiState by feedsViewModel.uiState.collectAsState()
    Box {
        Column {
            // 타이틀과 추가버튼이 있는 툴바
            torangToolbar.invoke()
            errorComponent.invoke()
            Box {
                // 피드와 스와이프 리프레시
                feeds.invoke()
                errorComponent.invoke()
            }
        }
        if (uiState.isExpandMenuBottomSheet)
            feedMenuBottomSheetDialog.invoke(true)
        if (uiState.isExpandCommentBottomSheet)
            commentBottomSheetDialog.invoke(true)
        if (uiState.isShareCommentBottomSheet) {
            shareBottomSheetDialog.invoke(true)
        }
        uiState.error?.let {
            AlertDialog(
                onDismissRequest = { feedsViewModel.removeErrorMsg() },
                confirmButton = {
                    Button(onClick = { feedsViewModel.removeErrorMsg() }) {
                        Text(text = "확인", color = Color.White)
                    }
                },
                title = { Text(text = it) })
        }
    }
}
```

### 최신 아키텍처 적용

### UIState 활용
```
data class FeedUiState(
    val isRefreshing: Boolean = false                   // 스크롤 리프레시
    , val list: List<FeedData> = ArrayList()            // 피드 리스트
    , val isExpandMenuBottomSheet: Boolean = false      // 피드메뉴 show 유무
    , val isExpandCommentBottomSheet: Boolean = false   // 커멘트창 show 유무
    , val isShareCommentBottomSheet: Boolean = false    // 공유창 show 유무
    , val isFailedLoadFeed: Boolean = false             // 피드 로딩 실패
    , val selectedReviewId: Int? = null                 // 선택한 리뷰
    , val comments: List<CommentData>? = null           // 커멘트 리스트
    , val myProfileUrl: String? = null                  // 커멘트 하단에 사용할 프로필 url
    , val error: String? = null                         // 에러 메시지
)
```
### ViewModel 구현
```
@HiltViewModel
class FeedsViewModel @Inject constructor(
    private val feedService: FeedService
) : ViewModel() {

    // UIState
    private val _uiState = MutableStateFlow(FeedUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            feedService.feeds.collect { newData -> _uiState.update { it.copy(list = newData) } } // feed 리스트 수집
            getFeed() // feed 가져오기
        }
    }

    // 피드 리스트 갱신
    fun refreshFeed() {...}

    // 피드 가져오기
    private suspend fun getFeed() {...}

    // 커멘트 가져오기
    fun onComment(reviewId: Int) {...}

    // 공유 클릭
    fun onShare() {...}

    // 즐겨찾기 클릭
    fun onFavorite(reviewId: Int) {...}

    // 좋아여 클릭
    fun onLike(reviewId: Int) {...}

    // 메뉴 닫기
    fun closeMenu() {...}

    // 코멘트창 닫기
    fun closeComment() {...}

    // 공유창 닫기
    fun closeShare() {...}

    // 메뉴 열기
    fun onMenu() {...}

    // 코멘트 작성하기
    fun sendComment(comment: String) {...}

    // 에러메시지 삭제
    fun removeErrorMsg() {...}

}
```

### 화면 UnitTest 작성

### Preview