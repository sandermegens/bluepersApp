import { IWedstrijd } from 'app/shared/model//wedstrijd.model';
import { ITeam } from 'app/shared/model//team.model';

export interface ICompetitie {
    id?: number;
    seizoen?: string;
    wedstrijds?: IWedstrijd[];
    teams?: ITeam[];
}

export class Competitie implements ICompetitie {
    constructor(public id?: number, public seizoen?: string, public wedstrijds?: IWedstrijd[], public teams?: ITeam[]) {}
}
