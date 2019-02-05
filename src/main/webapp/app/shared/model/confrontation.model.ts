import { Moment } from 'moment';

export interface IConfrontation {
    id?: number;
    dataConfronto?: Moment;
    scorePlayer1?: number;
    scorePlayer2?: number;
    penaltyPlayer1?: number;
    penaltyPlayer2?: number;
    player1PlayerName?: string;
    player1Id?: number;
    player2PlayerName?: string;
    player2Id?: number;
    roundRoundName?: string;
    roundId?: number;
}

export class Confrontation implements IConfrontation {
    constructor(
        public id?: number,
        public dataConfronto?: Moment,
        public scorePlayer1?: number,
        public scorePlayer2?: number,
        public penaltyPlayer1?: number,
        public penaltyPlayer2?: number,
        public player1PlayerName?: string,
        public player1Id?: number,
        public player2PlayerName?: string,
        public player2Id?: number,
        public roundRoundName?: string,
        public roundId?: number
    ) {}
}
