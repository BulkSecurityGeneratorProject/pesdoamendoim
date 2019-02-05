export interface IGroupe {
    id?: number;
    groupName?: string;
    competitionCompetitionName?: string;
    competitionId?: number;
}

export class Groupe implements IGroupe {
    constructor(public id?: number, public groupName?: string, public competitionCompetitionName?: string, public competitionId?: number) {}
}
