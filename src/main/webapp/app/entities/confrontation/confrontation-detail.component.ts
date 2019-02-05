import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IConfrontation } from 'app/shared/model/confrontation.model';

@Component({
    selector: 'jhi-confrontation-detail',
    templateUrl: './confrontation-detail.component.html'
})
export class ConfrontationDetailComponent implements OnInit {
    confrontation: IConfrontation;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ confrontation }) => {
            this.confrontation = confrontation;
        });
    }

    previousState() {
        window.history.back();
    }
}
