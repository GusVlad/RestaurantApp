interface CardRepository {

    fun addCard(request: AddCardEntity): Single<CardEntity>

    fun getCurrentCard(): Single<List<CardEntity>>

    fun getMyCard(token: String): Single<CardEntity>

    fun validateCard(request: ValidateCardRequestEntity): Single<ValidateCardResponseEntity>

    fun getCards(): Single<PageEntity<CardEntity>>

    fun setHideFlagExpirationDialog(id: Long): Completable

    fun getFlagExpirationDialog(id: Long?): Single<Boolean>

    fun checkCard(request: CheckCardRequestEntity): Single<CheckCardResponseEntity>
}
