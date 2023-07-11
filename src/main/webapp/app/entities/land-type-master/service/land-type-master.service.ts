import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ILandTypeMaster, NewLandTypeMaster } from '../land-type-master.model';

export type PartialUpdateLandTypeMaster = Partial<ILandTypeMaster> & Pick<ILandTypeMaster, 'id'>;

export type EntityResponseType = HttpResponse<ILandTypeMaster>;
export type EntityArrayResponseType = HttpResponse<ILandTypeMaster[]>;

@Injectable({ providedIn: 'root' })
export class LandTypeMasterService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/land-type-masters');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(landTypeMaster: NewLandTypeMaster): Observable<EntityResponseType> {
    return this.http.post<ILandTypeMaster>(this.resourceUrl, landTypeMaster, { observe: 'response' });
  }

  update(landTypeMaster: ILandTypeMaster): Observable<EntityResponseType> {
    return this.http.put<ILandTypeMaster>(`${this.resourceUrl}/${this.getLandTypeMasterIdentifier(landTypeMaster)}`, landTypeMaster, {
      observe: 'response',
    });
  }

  partialUpdate(landTypeMaster: PartialUpdateLandTypeMaster): Observable<EntityResponseType> {
    return this.http.patch<ILandTypeMaster>(`${this.resourceUrl}/${this.getLandTypeMasterIdentifier(landTypeMaster)}`, landTypeMaster, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ILandTypeMaster>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ILandTypeMaster[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getLandTypeMasterIdentifier(landTypeMaster: Pick<ILandTypeMaster, 'id'>): number {
    return landTypeMaster.id;
  }

  compareLandTypeMaster(o1: Pick<ILandTypeMaster, 'id'> | null, o2: Pick<ILandTypeMaster, 'id'> | null): boolean {
    return o1 && o2 ? this.getLandTypeMasterIdentifier(o1) === this.getLandTypeMasterIdentifier(o2) : o1 === o2;
  }

  addLandTypeMasterToCollectionIfMissing<Type extends Pick<ILandTypeMaster, 'id'>>(
    landTypeMasterCollection: Type[],
    ...landTypeMastersToCheck: (Type | null | undefined)[]
  ): Type[] {
    const landTypeMasters: Type[] = landTypeMastersToCheck.filter(isPresent);
    if (landTypeMasters.length > 0) {
      const landTypeMasterCollectionIdentifiers = landTypeMasterCollection.map(
        landTypeMasterItem => this.getLandTypeMasterIdentifier(landTypeMasterItem)!
      );
      const landTypeMastersToAdd = landTypeMasters.filter(landTypeMasterItem => {
        const landTypeMasterIdentifier = this.getLandTypeMasterIdentifier(landTypeMasterItem);
        if (landTypeMasterCollectionIdentifiers.includes(landTypeMasterIdentifier)) {
          return false;
        }
        landTypeMasterCollectionIdentifiers.push(landTypeMasterIdentifier);
        return true;
      });
      return [...landTypeMastersToAdd, ...landTypeMasterCollection];
    }
    return landTypeMasterCollection;
  }
}
