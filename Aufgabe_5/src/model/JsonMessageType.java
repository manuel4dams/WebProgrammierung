package model;

/**
 * @author Manuel Adams
 * @since 2019-01-10
 */
public enum JsonMessageType {

    LoginRequest,
    LoginResponse,
    GameStartRequest,
    GameStartResponse,
    MovementRequest,
    MovementResponse,
    PlayerListResponse,
    GameWonResponse,
    Error
}
