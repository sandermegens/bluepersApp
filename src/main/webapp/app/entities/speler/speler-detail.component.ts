import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISpeler } from 'app/shared/model/speler.model';

@Component({
    selector: 'jhi-speler-detail',
    templateUrl: './speler-detail.component.html'
})
export class SpelerDetailComponent implements OnInit {
    speler: ISpeler;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ speler }) => {
            this.speler = speler;
        });
    }

    previousState() {
        window.history.back();
    }
}
