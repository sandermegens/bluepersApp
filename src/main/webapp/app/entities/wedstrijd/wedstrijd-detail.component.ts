import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IWedstrijd } from 'app/shared/model/wedstrijd.model';

@Component({
    selector: 'jhi-wedstrijd-detail',
    templateUrl: './wedstrijd-detail.component.html'
})
export class WedstrijdDetailComponent implements OnInit {
    wedstrijd: IWedstrijd;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ wedstrijd }) => {
            this.wedstrijd = wedstrijd;
        });
    }

    previousState() {
        window.history.back();
    }
}
