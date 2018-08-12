import { ICompetitie } from 'app/shared/model//competitie.model';
import { ISpeler } from 'app/shared/model//speler.model';
import { IWedstrijd } from 'app/shared/model//wedstrijd.model';

export interface ITeam {
    id?: number;
    naam?: string;
    competities?: ICompetitie[];
    spelers?: ISpeler[];
    wedstrijds?: IWedstrijd[];
}

export class Team implements ITeam {
    constructor(
        public id?: number,
        public naam?: string,
        public competities?: ICompetitie[],
        public spelers?: ISpeler[],
        public wedstrijds?: IWedstrijd[]
    ) {}
}
