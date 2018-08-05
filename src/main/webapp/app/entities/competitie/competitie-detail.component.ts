import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICompetitie } from 'app/shared/model/competitie.model';

@Component({
    selector: 'jhi-competitie-detail',
    templateUrl: './competitie-detail.component.html'
})
export class CompetitieDetailComponent implements OnInit {
    competitie: ICompetitie;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ competitie }) => {
            this.competitie = competitie;
        });
    }

    previousState() {
        window.history.back();
    }
}
