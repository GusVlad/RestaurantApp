interface CardRestService {

    @PUT(CARD_ADD_CARD)
    fun addCard(@Body request: AddCardRequest): Single<CardResponse>

    @GET(CARD_CURRENT_CARD)
    fun getCurrentCard(): Single<List<CardResponse>>

    @GET(CARD_MY_CARD)
    fun getMyCard(@Query(TOKEN) token: String, @Query(TYPE) type: String): Single<CardResponse>

    @PUT(CARD_VALIDATE_CARD)
    fun validateCard(@Body request: ValidateCardRequest): Single<ValidateCardResponse>

    @PUT(CARD_GET_CARDS)
    fun getCards(): Single<PageResponse<CardResponse, CardEntity>>

    @PUT(CARD_CHECK_CARD)
    fun checkCard(@Body request: CheckCardRequest): Single<CheckCardResponse>
}