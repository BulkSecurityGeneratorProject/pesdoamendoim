export interface ICompetition {
    id?: number;
    competitionName?: string;
}

export class Competition implements ICompetition {
    constructor(public id?: number, public competitionName?: string) {}
}
