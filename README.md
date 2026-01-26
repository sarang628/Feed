# Feed Module

피드(feed): 스크롤해서 볼 수 있는 콘텐츠 스트림

- 여러화면에서 사용되는 피드항목(FeedItem)은 공통 모듈로 구현
- 피드 리스트를 피드항목에 함께 구현을 고민하다 분리하기로 결정 
  - 무한 스크롤, 당겨서 새로고침 등의 기능 구현
  - 화면마다 조금씩 기능이 다른 피드 리스트가 필요

[Introduce.md](./docs/Introduce.md)

## Jitpack 설정

settings.gradle.kts

```
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io") -- 추가
    }
}
```

build.gradle.kts

```
dependencies {
    implementation ("com.github.sarang628:Feed:17428f3a58") -- 추가
}
```

https://jitpack.io/#sarang628/Feed 에서 최신(커밋 해시) 버전 확인

## Empty

<img src="screenshots/empty.gif" width="300">

```
@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun FeedScreenEmptyPreview() {
    FeedScreen(uiState = FeedUiState.Empty)
}
```

## Loading

<img src="screenshots/loading.gif" width="300">

```
@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun FeedScreenLoadingPreview() {
    FeedScreen(uiState = FeedUiState.Loading)
}
```

## Loading Success

<img src="screenshots/success.gif" width="300">

```
@OptIn(ExperimentalMaterial3Api::class)
@Preview(backgroundColor = 0xFFFDFDF6, showBackground = true)
@Composable
fun FeedScreenSuccessPreview() {
    FeedScreen(/*Preview*/
        uiState = FeedUiState.Success(
            list = listOf(
                Feed.Sample, Feed.Sample, Feed.Sample, Feed.Sample, Feed.Sample, Feed.Sample, Feed.Empty, Feed.Empty, Feed.Empty, Feed.Empty, Feed.Empty, Feed.Empty, Feed.Empty, Feed.Empty, Feed.Empty
            )
        )
    )
}
```

# Tech Stack
- JetpackCompose
- Android App Architecture
  - UILayer
  - DomainLayer