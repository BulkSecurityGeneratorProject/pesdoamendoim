import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IConfrontation } from 'app/shared/model/confrontation.model';
import { ConfrontationService } from './confrontation.service';

@Component({
    selector: 'jhi-confrontation-delete-dialog',
    templateUrl: './confrontation-delete-dialog.component.html'
})
export class ConfrontationDeleteDialogComponent {
    confrontation: IConfrontation;

    constructor(
        protected confrontationService: ConfrontationService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.confrontationService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'confrontationListModification',
                content: 'Deleted an confrontation'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-confrontation-delete-popup',
    template: ''
})
export class ConfrontationDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ confrontation }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ConfrontationDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.confrontation = confrontation;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/confrontation', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/confrontation', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
