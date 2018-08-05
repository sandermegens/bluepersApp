import { Moment } from 'moment';
import { ISpeler } from 'app/shared/model//speler.model';

export interface IWedstrijd {
    id?: number;
    datum?: Moment;
    tijd?: Moment;
    plaats?: string;
    spelers?: ISpeler[];
    competitieId?: number;
}

export class Wedstrijd implements IWedstrijd {
    constructor(
        public id?: number,
        public datum?: Moment,
        public tijd?: Moment,
        public plaats?: string,
        public spelers?: ISpeler[],
        public competitieId?: number
    ) {}
}
