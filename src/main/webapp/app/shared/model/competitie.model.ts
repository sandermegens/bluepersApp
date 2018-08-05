import { IWedstrijd } from 'app/shared/model//wedstrijd.model';

export interface ICompetitie {
    id?: number;
    seizoen?: string;
    wedstrijds?: IWedstrijd[];
}

export class Competitie implements ICompetitie {
    constructor(public id?: number, public seizoen?: string, public wedstrijds?: IWedstrijd[]) {}
}
