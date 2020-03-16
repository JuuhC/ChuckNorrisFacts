package com.carrati.chucknorrisfacts.presentationTest

import android.content.Intent
import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.agoda.kakao.image.KImageView
import com.agoda.kakao.recycler.KRecyclerItem
import com.agoda.kakao.recycler.KRecyclerView
import com.agoda.kakao.screen.Screen
import com.agoda.kakao.text.KButton
import com.agoda.kakao.text.KTextView
import com.carrati.chucknorrisfacts.R
import com.carrati.chucknorrisfacts.presentation.activities.MainActivity
import com.carrati.chucknorrisfacts.presentation.adapters.FactsAdapter
import okhttp3.internal.wait
import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

class Item(parent: Matcher<View>) : KRecyclerItem<Item>(parent) {
    val fact = KTextView(parent) { withId(R.id.tvFact) }
    val category = KTextView(parent) { withId(R.id.tvFactCategory) }
    val shareButton = KImageView(parent) { withId(R.id.btShareFact) }
}

class MainActivityScreen : Screen<MainActivityScreen>(){
    val searchButton = KButton { withId(R.id.action_search) }
    val recycler = KRecyclerView({
        withId(R.id.recyclerView)
    }, itemTypeBuilder = {
        itemType(::Item)
    })
}

@LargeTest
class MainActivityTest {

    private lateinit var mockServer: MockWebServer
    private val screen = MainActivityScreen()

    @Rule
    @JvmField
    val rule = ActivityTestRule(MainActivity::class.java, false, false)

    @Before
    fun setUp(){
        mockServer = MockWebServer()
        mockServer.start(8080)
    }

    @After
    fun shutDown() {
        mockServer.shutdown()
    }

    @Test
    fun test_success_listFacts() {
        val intent = Intent(InstrumentationRegistry.getInstrumentation().targetContext, MainActivity::class.java)
        rule.launchActivity(intent)

        val recyclerView = Espresso.onView(
            Matchers.allOf(
                withId(R.id.recyclerView),
                isDisplayed()
            )
        )
        //else, recycler view visibility is gone
        recyclerView.check(ViewAssertions.matches(isDisplayed()))
    }

    @Test
    fun test_success_getRandomFact() {
        mockServer.dispatcher = MockServerDispatcher.ResponseDispatcher()
        val intent = Intent(InstrumentationRegistry.getInstrumentation().targetContext, MainActivity::class.java)
        //  passing valid MOCK url to the activity
        intent.putExtra(
            InstrumentationRegistry.getInstrumentation().targetContext.getString(R.string.base_url),
            mockServer.url("/random").toString()
        )
        rule.launchActivity(intent)

        screen {
            searchButton {
                isDisplayed()
                isClickable()
                click()
            }
            recycler {
                hasSize(1)
                scrollTo(0)
                childAt<Item>(0){
                    fact.hasAnyText()
                    category.isDisplayed()
                    category.hasText("uncategorized")
                    shareButton.isDisplayed()
                    shareButton.isClickable()
                }
            }
        }
    }

    @Test
    fun test_fail_getRandomFact(){
        mockServer.dispatcher = MockServerDispatcher.ErrorDispatcher()

        val intent = Intent(InstrumentationRegistry.getInstrumentation().targetContext, MainActivity::class.java)
        intent.putExtra(
            InstrumentationRegistry.getInstrumentation().targetContext.getString(R.string.base_url),
            mockServer.url("/random").toString()
        )
        rule.launchActivity(intent)

        screen {
            searchButton {
                isDisplayed()
                isClickable()
                click()
            }
        }

        onView(withText("Sem conexão, tente novamente mais tarde")).check(ViewAssertions.matches(isDisplayed()))
    }

    @Test
    fun test_share_fact() {
        mockServer.dispatcher =
            MockServerDispatcher.ResponseDispatcher()
        val intent = Intent(InstrumentationRegistry.getInstrumentation().targetContext, MainActivity::class.java)
        //  passing valid MOCK url to the activity
        intent.putExtra(
            InstrumentationRegistry.getInstrumentation().targetContext.getString(R.string.base_url),
            mockServer.url("/random").toString()
        )
        rule.launchActivity(intent)

        screen {
            searchButton {
                isDisplayed()
                isClickable()
                click()
            }
            recycler {
                hasSize(1)
                scrollTo(0)
                childAt<Item>(0){
                    shareButton {
                        isDisplayed()
                        isClickable()
                        click()
                    }
                }
            }
        }

        Intents.intended(IntentMatchers.hasAction(CoreMatchers.equalTo(Intent.ACTION_SEND)))
    }
}