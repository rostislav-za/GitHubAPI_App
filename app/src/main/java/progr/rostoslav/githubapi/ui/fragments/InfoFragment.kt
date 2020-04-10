package progr.rostoslav.githubapi.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.include_recycler.*
import progr.rostoslav.githubapi.R
import progr.rostoslav.githubapi.entities.TaskInfo
import progr.rostoslav.githubapi.ui.fragments.bases.BaseFragment
import progr.rostoslav.githubapi.ui.recycler.adapters.TaskAdapter


class InfoFragment : BaseFragment() {
    val adapter = TaskAdapter()
    val list = listOf<TaskInfo>(
        TaskInfo(
            title = "  1) Главный экран приложения - список репозиториев запрошенных по урлу https://api.github.com/repositories",
            text = " для получения дополнительной информации о репозитории нужно делать отдельный запрос для каждого репозитория на урл https://api.github.com/repos/<REPO_full_name> Каждый репозиторий должен содержать:\n" +
                    "- название\n" +
                    "- описание (должно отображаться полностью, ячейки должны растягиваться соответствующим образом)\n" +
                    "- язык программирования, если есть\n" +
                    "- количество форков\n" +
                    "- количество звезд\n" +
                    "- имя автора и фото (должно быть круглым)"
        ),
        TaskInfo(
            title = "2) Открытие детальной информации о репозитории",
            text = "По нажатию на элемент списка должен открываться отдельный экран с детальной информацией о репозитории",
            isReady = true
        ),
        TaskInfo(
            title = "Обязательно должны отображаться:",
            text = "- Название\n" +
                    "- Информация об авторе (имя и аватар)",
            isReady = false
        ),
        TaskInfo(
            title = "- Последние 10 коммитов",
            text = "\t- описание (сообщение) коммита\n" +
                    "\t- дата\n" +
                    "\t- имя и аватар автора",
            isReady = true
        ),
        TaskInfo(
            title = "исправить баг с core.js",
            text = "Не дает распарсить список коммитов для этого репозитория",
            isReady = true
        ),
        TaskInfo(
            title = "3) Сделать 2 список репозиториев ",
            text = "Реализовать второй экран с тем же интерфейсом (использовать те же UI компоненты, которые были реализованы на первых шагах), на данном этапе можно наполнить теми же данными, в дальнейшем на этом экране будут отображаться избранные репозитории\n" +
                    "Переключение между экранами должно быть сделано с помощью табов внизу экрана\n" +
                    "На новом экране со списком репозиториев так же должно работать открытие экрана детальной информации о репозитории",
            isReady = true
        ),
        TaskInfo(
            title = "4) Сделать сохранение репозиториев в избранное",
            text = "На первом экране со списком репозиториев сделать возможность добавить репозиторий в избранное (сохранить в локальном хранилище - на свое усмотрение).\n" +
                    "На втором экране со списком репозиториев отображать репозитории загруженные из локального хранилища (должно работать без доступа в интернет)\n" +
                    "Репозиторий должен добавляться и удаляться из избранного. \n" +
                    "Кнопка добавления/удаления в избранное должна быть доступна в списке репозиториев и на экране детальной информации о репозитории",
            isReady = true
        ),
        TaskInfo(
            title = "5) Добавить анимацию загрузки данных.",
            text = "В момент когда мы попадаем на экраны списка репозиториев или детальной страницы и данные еще не загрузились - отображать экран с анимированным элементом - на свой выбор"
            , isReady = true
        ),
        TaskInfo(
            title = "6) Pull-to-refresh на экране списка репозиториев",
            text = "Возможность перезагрузить список за счет использования контрола pull-to-refresh Разрешается использовать стороннюю библиотеку"
            , isReady = true
        ),
        TaskInfo(
            title = "7) Реализовать экран авторизации",
            text = "При первом запуске приложения первым должен открываться экран авторизации, содержащий поля для ввода логина и пароля на github и кнопку авторизации. В случае удачного ввода логина и пароля и нажатия на кнопку авторизации " +
                    "приложение должно переходить на ранее созданные экраны (списки репозиториев). Верно введенные логин и пароль должны сохраняться в приложении и использоваться при перезапуске приложения для автоматического перехода на экран со списком репозиториев. В качестве механизма авторизации можно использовать Basic Auth, описанный в начале документа."
            , isReady = true
        ),
        TaskInfo(
            title = "8) Реализовать деавторизацию",
            text = "Реализовать деавторизацию пользователя. Сделать интерфейс (кнопку, экран или раздел приложения) позволяющий пользователю деавторизоваться и попасть на экран авторизации."
            , isReady = true
        ),
        TaskInfo(
            title = "9) Реализовать привязку избранного к логину пользователя",
            text = "Каждый авторизованный пользователь должен видеть свои избранные репозитории. То есть избранное пользователя A должно не зависеть от избранного пользователя B"
            , isReady = true
        ),
        TaskInfo(
            title = "10*) Вынести строки в ресурсы",
            text = ""
        ),
        TaskInfo(
            title = "11*) Добавить шифрование к sharedPref",
            text = ""
        ),
        TaskInfo(
            title = "12*)реализовать ключ как логина, зашифрованный паролем ",
            text = ""
        )
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.fragment_info, container, false)

    override fun init() {
        super.init()
        val divider = DividerItemDecoration(activity, DividerItemDecoration.VERTICAL)
        rv.layoutManager = LinearLayoutManager(activity)
        rv.adapter = adapter
        rv.addItemDecoration(divider)
    }

    override fun setContent() {
        super.setContent()
        adapter.setList(list)
    }
}
