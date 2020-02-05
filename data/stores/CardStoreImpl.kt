class CardStoreImpl @Inject constructor(private val service: CardRestService) : CardStore {

    override fun addCard(request: AddCardEntity): Single<CardEntity> {
        return Single.just(request)
            .map { AddCardRequest(it) }
            .flatMap { service.addCard(it) }
            .map { it.toEntity() }
    }

    override fun getCurrentCard(): Single<List<CardEntity>> {
        return service.getCurrentCard()
            .onErrorResumeNext { throwable -> checkForNoCurrentCardException(throwable) }
            .map { it.map { it.toEntity() } }
            .map { it.toList() }
    }

    override fun getMyCard(token: String): Single<CardEntity> {
        return service.getMyCard(token, DEVICE_TYPE)
            .onErrorResumeNext { throwable -> checkForNoMyCardException(throwable) }
            .map { it.toEntity()  }
    }

    override fun validateCard(request: ValidateCardRequestEntity): Single<ValidateCardResponseEntity> {
        return Single.just(request)
            .map { ValidateCardRequest(it) }
            .flatMap { service.validateCard(it) }
            .map { it.toEntity() }
    }

    private fun checkForNoMyCardException(throwable: Throwable): SingleSource<out CardResponse> {
        return if (throwable is EOFException) {
            val message = throwable.message ?: ""
            Single.error(NoMyCardException(message))
        } else {
            Single.error(throwable)
        }
    }

    private fun checkForNoCurrentCardException(throwable: Throwable): SingleSource<out List<CardResponse>> {
        return if (throwable is HttpException) {
            val errorBodyString = throwable.response().errorBody()?.string()
            val errorBody = GsonHolder.GSON.fromJson(errorBodyString, ErrorBody::class.java)
            if (NoCurrentCardException.STATUS_CODE == errorBody.statusCode) {
                Single.error(NoCurrentCardException(throwable.message()))
            } else {
                Single.error(throwable)
            }
        } else {
            Single.error(throwable)
        }
    }

    override fun getCards() = service.getCards().map { it.toEntity() }

    override fun checkCard(request: CheckCardRequestEntity): Single<CheckCardResponseEntity> {
        return Single.just(request)
            .map { CheckCardRequest(it) }
            .flatMap { service.checkCard(it) }
            .map { it.toEntity() }
    }
}