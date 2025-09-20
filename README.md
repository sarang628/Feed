# Feed Module

피드(feed): 스크롤해서 볼 수 있는 콘텐츠 스트림

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

<img src="screenshots/preview3.gif" width="300">

```
@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun FeedScreenLoadingPreview() {
    FeedScreen(uiState = FeedUiState.Loading)
}
```

## Loading Success

<img src="screenshots/preview4.gif" width="300">

```
@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun FeedScreenSuccessPreview() {
    FeedScreen(
        //@formatter:off
        uiState = FeedUiState.Success(list = listOf(Feed.Empty, Feed.Empty, Feed.Empty, Feed.Empty, Feed.Empty, Feed.Empty, Feed.Empty, Feed.Empty, Feed.Empty, Feed.Empty, Feed.Empty, Feed.Empty, Feed.Empty, Feed.Empty, Feed.Empty)),
        //@formatter:on
        feed = {
            Box(
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = 5.dp)
                    .background(Color(0xAAEEEEEE))
                    .height(80.dp)
            ) { Text("feed") }
        }
    )
}
```

## 테스트 환경

Android Studio Meerkat | 2024.3.1 Patch 1<br>
gradle-8.11.1-bin
Gradle JDK: corretto-17.0.14
minSdk: 26
