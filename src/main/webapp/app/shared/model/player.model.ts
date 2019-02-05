export interface IPlayer {
    id?: number;
    playerName?: string;
}

export class Player implements IPlayer {
    constructor(public id?: number, public playerName?: string) {}
}
