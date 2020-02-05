interface CardStore {

    fun addCard(request: AddCardEntity): Single<CardEntity>

    fun getCurrentCard(): Single<List<CardEntity>>

    fun getMyCard(token: String): Single<CardEntity>

    fun validateCard(request: ValidateCardRequestEntity): Single<ValidateCardResponseEntity>

    fun getCards(): Single<PageEntity<CardEntity>>

    fun checkCard(request: CheckCardRequestEntity): Single<CheckCardResponseEntity>
}