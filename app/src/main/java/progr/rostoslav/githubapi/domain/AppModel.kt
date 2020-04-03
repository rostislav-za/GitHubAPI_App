package progr.rostoslav.githubapi.domain

import progr.rostoslav.githubapi.data.DataRepository
import progr.rostoslav.githubapi.entities.Rep
import progr.rostoslav.githubapi.entities.toRepInfo
import progr.rostoslav.githubapi.ui.DataManager

/*
* Мы запускаем приложение, в модель идет об этом сигнал.
* в модель шлется сигнал о создании приложения .
* модель отвечает командой  покажи загрузку(в дата менеджер)во вью
*  и идет за данными в репозиторий
 когда репозиторий возвращает  данные, мы отправляем их в дата менеджер,  интерфейс обновяется
   нам приходит от пользователя запрос на обновление данных, мы посылаем за данными запрос в репозиторий.
   * репозиторий дает данные, синхроним их и суем в менеджер.
   *
   *  пользователь закрывает приложение, вью оповещает нас об этом. мы сохраняем  список избранного в бд*
 *
 *
*
* */

class AppModel() {
    val dr = DataRepository()
    fun onCreate() {
        dr.init()
        dr.getReps("octokit")
        dr.getRepInfo("rostislav-za", "GitHubAPI")
        dr.getCommits(10)
    }

    fun onDestroy() {
      saveData()

    }


    fun updateData() {
        dr.getReps("octokit")
        dr.getRepInfo("rostislav-za", "GitHubAPI")
        dr.getCommits(10)
    }

    fun repItemClicked(rep: Rep) {
        DataRepository().getRepInfo(rep)
        DataManager.udateRepInfo(rep.toRepInfo())
    }

    fun repIsSavedChanged(rep: Rep) {
        val copy = rep.copy(isSaved = !rep.isSaved)
        DataManager.updateRep(rep, copy)
    }

    fun saveData() {
        dr.saveReps(DataManager.getFauvReps())
    }


}
