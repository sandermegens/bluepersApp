/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BluepersAppTestModule } from '../../../test.module';
import { WedstrijdDetailComponent } from 'app/entities/wedstrijd/wedstrijd-detail.component';
import { Wedstrijd } from 'app/shared/model/wedstrijd.model';

describe('Component Tests', () => {
    describe('Wedstrijd Management Detail Component', () => {
        let comp: WedstrijdDetailComponent;
        let fixture: ComponentFixture<WedstrijdDetailComponent>;
        const route = ({ data: of({ wedstrijd: new Wedstrijd(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [BluepersAppTestModule],
                declarations: [WedstrijdDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(WedstrijdDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(WedstrijdDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.wedstrijd).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
