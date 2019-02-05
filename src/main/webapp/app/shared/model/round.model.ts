export interface IRound {
    id?: number;
    roundName?: string;
    groupeGroupName?: string;
    groupeId?: number;
}

export class Round implements IRound {
    constructor(public id?: number, public roundName?: string, public groupeGroupName?: string, public groupeId?: number) {}
}
